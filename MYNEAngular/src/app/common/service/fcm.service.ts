import { Injectable } from '@angular/core';
import { getMessaging, getToken, onMessage } from "firebase/messaging";
// import { onBackgroundMessage } from "firebase/messaging/sw";
// import { getMessaging as getMessagingBg} from "firebase/messaging/sw";
import { environment } from 'src/environments/environment';
import { NotificationService } from 'src/app/common/shared/message/notification.service';

@Injectable({
  providedIn: 'root'
})
export class FcmService {

  constructor(private notifyService: NotificationService) { }
  message:any = null;
  init() {
    this.requestPermission();
    this.listenFg();
  }

  requestPermission() {
    const messaging = getMessaging();
    getToken(messaging,{vapidKey: environment.firebaseConfig.vapidKey})
      .then((currentToken) => {
        if (currentToken) {
          console.log(currentToken);
        } else {
          console.log('No registration token available. Request permission to generate one.');
        }
      }).catch((err) => {
        console.log(err);
      });
  }

  listenFg() {
    const messaging = getMessaging();
    onMessage(messaging, (payload) => {
      //console.log('Message received. ', payload.data);
       this.notifyService.showNotification(payload.data.body,payload.data.title);
    });
  }
 
}