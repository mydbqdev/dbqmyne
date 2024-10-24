import { Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
@Injectable({
    providedIn:'root'
})

export class DataService{
    
    public isSale=new BehaviorSubject<boolean>(true);
    
    getIsSale=this.isSale.asObservable();
    setIsSale(isSale:boolean){
        console.info("hi mhi:"+isSale);
       this.isSale.next(isSale);
       console.info("hi fter mhi:"+this.isSale);

       console.info("hi fter2 mhi:"+this.getIsSale);
    }

}