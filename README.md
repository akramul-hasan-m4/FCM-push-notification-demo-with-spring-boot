# Project setup
**Step 1:** First create a project
**Step 2:** Then go to [Firebase](https://firebase.google.com/) and login by your gmail and create a new project.
*  Give project name
* Select your country
* Check accept and press create
* Then Select android icon from top nav
* Then copy your project name from appliction and paste it first box from firebase console
* You can give Debug signing certificate SHA-1 but it is optional . For creating certificate from your android studio click gradle from right side of screen , then click android > 	  singingReport . then see your console u found a sha-1 fingerprint . copy it and paste it in firbase requird field.

**Step 3:** Download **google-services.json** file and paste it on app folder (select project view first)
**Step 4:**
	1. Add dependency in project label - classpath 'com.google.gms:google-services:4.0.1'
	2. Add depency in app label -  implementation 'com.google.firebase:firebase-core:16.0.1' and  implementation 'com.google.firebase:firebase-messaging:17.3.4'
	3. If u have some support error then add dependency implementation 'com.android.support:support-v4:28.0.0'
	4. Appy pluging in app lavel bottom of the page apply plugin: 'com.google.gms.google-services'
**Step 5:** Then sync the project and run it then got to console and see verify installation success or not (bottom)
**Step 6:** Tehn create a service which extends FirebaseMessagingService and override onMessageReceived method like -
```java
		@Override
    	public void onMessageReceived(RemoteMessage remoteMessage) {
        	super.onMessageReceived(remoteMessage);
        	showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    	}

    	public void showNotification(String title, String message) {
       
        	NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "MyNotifications");
        	notification.setSmallIcon(R.drawable.ic_stat_name)
                	.setContentTitle(title)
                	.setContentText(message)
                	.setAutoCancel(true);
             

        	NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        	notificationManager.notify(999, notification.build());
    	}
```
**Step 7:** add some line in your activity class into oncreate method -
```java		

	     // this is for oreo and upper version
	    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            	NotificationChannel channel = new NotificationChannel("MyNotifications", "MyNotifications",NotificationManager.IMPORTANCE_DEFAULT);
            	NotificationManager manager = getSystemService(NotificationManager.class);
            	manager.createNotificationChannel(channel);
            }
            
            //FirebaseMessaging.getInstance().subscribeToTopic("general") or
        	FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       // String msg = getString(R.string.msg_subscribed);
                        String msg = "successfull";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }

                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }); 
 ```
## AndroidManifest.xml
**Step 8:** Add in menifest file into application tag -
```xml
	<service android:name=".MyFirebaseMessagingService">
        <intent-filter>
            <action android:name="com.google.firebase.MESSAGING_EVENT" />
        </intent-filter>
    </service>
```
**Step 9:** add permission into manifest tag 
```xml
<uses-permission android:name="android.permission.INTERNET" />
```
## Send Notification
**Step 10 :** For send notification from firbase console click **grow >cloud messging** 

	1. fillup title text and meesage from section 1
	2. from sec2 click on topic write general in feild (you can write other type)
	3. section 3 select schedul now
	4. section 5 write Notification chanel which u used in your project (my case MyNotifications)
	5. click reviw and publish
	6. then check your phone nofiction come or not

**Alternative**
```json
    {
      "to": 
        "/topics/NEWS"
      ,
      "data": {
        "extra_information": "This is some extra information"
      },
      "notification": {
        "title": "NEW NOTIFICATION!",
        "text": "Click me to open an Activity!",
        "click_action": "BlankFragment"
      }
    }
```
*Must add in Headers* -
    Accept : application/json
    Content-Type : application/json
    Authorization : key=*****your key*****
  