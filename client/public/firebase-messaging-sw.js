importScripts('https://www.gstatic.com/firebasejs/8.9.1/firebase-app.js')
importScripts('https://www.gstatic.com/firebasejs/8.9.1/firebase-messaging.js')

import firebase from 'firebase/app';

// firebase 서버 연동을 위한 Config
var firebaseConfig = {
  apiKey: "AIzaSyBrOa-v9yfOHgAmdf3k1IcaRoEEatKKQLc",
  authDomain: "commbfcm.firebaseapp.com",
  projectId: "commbfcm",
  storageBucket: "commbfcm.appspot.com",
  messagingSenderId: "630258256989",
  appId: "1:630258256989:web:2b1332259e75b71d0091b4"
};

const app = null;
const messaging = null;
if (firebase.messaging.isSupported()) {
  app = firebase.initializeApp(firebaseConfig)
  messaging = firebase.messaging()
}

