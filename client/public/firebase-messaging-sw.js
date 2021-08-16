importScripts('https://www.gstatic.com/firebasejs/8.9.1/firebase-app.js')
importScripts('https://www.gstatic.com/firebasejs/8.9.1/firebase-messaging.js')

// firebase 서버 연동을 위한 Config
var firebaseConfig = {
    apiKey: "AIzaSyBi-CjUpqtPjDFgo8jLiwxwpcm0KhWI31g",
    authDomain: "commb-43e85.firebaseapp.com",
    databaseURL: "https://commb-43e85-default-rtdb.asia-southeast1.firebasedatabase.app",
    projectId: "commb-43e85",
    storageBucket: "commb-43e85.appspot.com",
    messagingSenderId: "366820301866",
    appId: "1:366820301866:web:ef81fde40aeda933d63753"
};

const app = firebase.initializeApp(firebaseConfig)

const messaging = firebase.messaging()


