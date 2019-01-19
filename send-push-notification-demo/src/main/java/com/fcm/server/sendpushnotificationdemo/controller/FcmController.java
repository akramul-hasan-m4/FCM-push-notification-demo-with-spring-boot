package com.fcm.server.sendpushnotificationdemo.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fcm.server.sendpushnotificationdemo.entity.CustomerOffers;
import com.fcm.server.sendpushnotificationdemo.model.FcmData;
import com.fcm.server.sendpushnotificationdemo.model.FcmResponse;
import com.fcm.server.sendpushnotificationdemo.repo.CustomerOffersRepo;

@Controller
public class FcmController {
	
	@Autowired private CustomerOffersRepo dataRepo;
	
	@Value("${android.fcm.key}") private String androidFcmKey;
	@Value("${android.Fcm.Url}") private String androidFcmUrl;
	
	@GetMapping("/")
	public String customerOffer(Model model){
		model.addAttribute("CustomerOffer", new CustomerOffers());
		return "customerOffer";
	}
	
	@PostMapping("/offer")
	public String greetingSubmit(@ModelAttribute("CustomerOffer") CustomerOffers offer, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "Unable to save notification");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/";
			}
		offer.setOfferPublisedDate(LocalDate.now());
		offer.setIsNewOffer(Boolean.TRUE);
		dataRepo.save(offer);
		redirectAttributes.addFlashAttribute("message", "successfully save notification");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/";
	}
	
	@PostMapping("/emergency-send")
	public @ResponseBody ResponseEntity<CustomerOffers> saveUser(@Valid @RequestBody CustomerOffers offer, Errors errors) {
		HttpHeaders headers = new HttpHeaders();
		if (errors.hasErrors()) {
			headers.add("ERROR_MSG", "Unable to send notification");
			String errorResult = errors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
			ResponseEntity.badRequest().body(errorResult);
			return ResponseEntity.noContent().headers(headers).build();
		}
		offer.setOfferPublisedDate(LocalDate.now());
		offer.setIsNewOffer(Boolean.TRUE);
		
		if(sendEmergencyNotice(SchedulerController.show(offer))) {
			dataRepo.save(offer);
			headers.add("SUCCESS_MSG", "successfully send notification");
		}else {
			headers.add("ERROR_MSG", "Unable to send notification");
		}
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(offer.getOfferId()).toUri();
		headers.setLocation(location);
		
		return ResponseEntity.created(location).headers(headers).build();
	} 
	
	private boolean sendEmergencyNotice(FcmData fcmData) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		
		httpHeaders.set("Authorization", "key=" + androidFcmKey);
		httpHeaders.set("Content-Type", "application/json");
		
		HttpEntity<FcmData> httpEntity = new HttpEntity<>(fcmData, httpHeaders);
		
		ParameterizedTypeReference<FcmResponse> typeRef = new ParameterizedTypeReference<FcmResponse>() {};
		
		ResponseEntity<FcmResponse> rs = restTemplate.exchange(androidFcmUrl, HttpMethod.POST, httpEntity, typeRef);
		return rs.getStatusCode() == HttpStatus.OK? true : false;
	}
	
}
