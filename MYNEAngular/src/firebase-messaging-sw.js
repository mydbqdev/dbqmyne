importScripts('https://www.gstatic.com/firebasejs/11.0.1/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/11.0.1/firebase-messaging-compat.js');

var firebaseConfig ={
  apiKey: 'AIzaSyDwZKE-Y7hYvBIdRl7p4j_ui4AJveo1QMo',
  authDomain: 'zoy-mobile-app.firebaseapp.com',
  projectId: 'zoy-mobile-app',
  storageBucket: 'zoy-mobile-app.appspot.com',
  messagingSenderId: '694212252477',
  appId: '1:694212252477:web:3627a2a382cc57be46eb54',
  measurementId: 'G-GHJ6WR7MR9',
};

firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();
messaging.onBackgroundMessage((payload) => {
  // console.log('[firebase-messaging-sw.js] Received background message ', payload);
  // Customize notification here
  const notificationTitle = payload.data.title;
  const notificationOptions = {
    body: payload.data.body,
    icon: '/assets/images/myne-smll-logo-n2.png'
  };

  self.registration.showNotification(notificationTitle,
    notificationOptions);
});

