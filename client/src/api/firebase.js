import firebase from 'firebase/app'
import 'firebase/firebase-messaging'

const firebaseConfig = {
    apiKey: "AIzaSyBi-CjUpqtPjDFgo8jLiwxwpcm0KhWI31g",
    authDomain: "commb-43e85.firebaseapp.com",
    databaseURL: "https://commb-43e85-default-rtdb.asia-southeast1.firebasedatabase.app",
    projectId: "commb-43e85",
    storageBucket: "commb-43e85.appspot.com",
    messagingSenderId: "366820301866",
    appId: "1:366820301866:web:ef81fde40aeda933d63753"
  }

firebase.initializeApp(firebaseConfig)

export default firebase.messaging()