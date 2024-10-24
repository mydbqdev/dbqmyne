import { Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import { User } from '../shared/user';
import { SignupDetails } from '../shared/signup-details';
@Injectable({
    providedIn:'root'
})

export class DataService{
    
    public isSale=new BehaviorSubject<boolean>(true);
    public userDetails=new BehaviorSubject<SignupDetails>(null);
    getIsSale=this.isSale.asObservable();
    setIsSale(isSale:boolean){
        console.info("hi mhi:"+isSale);
       this.isSale.next(isSale);
       console.info("hi fter mhi:"+this.isSale);

       console.info("hi fter2 mhi:"+this.getIsSale);
    }

    getUserDetails=this.userDetails.asObservable();

    setUserDetails(userDetails:SignupDetails){
       
       this.userDetails.next(userDetails);
     
    }

}