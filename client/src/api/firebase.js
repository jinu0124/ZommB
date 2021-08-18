import firebase from 'firebase/app'
import 'firebase/firebase-messaging'

const firebaseConfig = {
  apiKey: "AIzaSyBrOa-v9yfOHgAmdf3k1IcaRoEEatKKQLc",
  authDomain: "commbfcm.firebaseapp.com",
  projectId: "commbfcm",
  storageBucket: "commbfcm.appspot.com",
  messagingSenderId: "630258256989",
  appId: "1:630258256989:web:2b1332259e75b71d0091b4"
}

let messaging = null

if (firebase.messaging.isSupported()) {
  firebase.initializeApp(firebaseConfig)
  messaging = firebase.messaging()
}

export default messaging