import { register } from 'register-service-worker'
import firebase from 'firebase';

register = null;
if (firebase.messaging.isSupported()) {
    register('firebase-messaging-sw.js')
}

export default register()