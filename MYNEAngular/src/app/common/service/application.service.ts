import { Injectable, Inject } from '@angular/core';
import {HttpClient,HttpHeaders,HttpErrorResponse} from '@angular/common/http';
import { MessageService } from 'src/app/message.service';
import { BASE_PATH } from '../shared/variables';
import { Observable, of} from 'rxjs';
import { Router } from '@angular/router';
import { AESEncryptDecryptHelper } from '../shared/AESEncryptDecryptHelper';
import { ServiceHelper } from '../shared/service-helper';
import { SearchRequest } from '../models/search-request.model';
@Injectable({
    providedIn:'root'
})

export class AppService{
    
    public oDataBlockSize:number;
    public baseODataUrl:string;
    httpOptions ={
        headers :new HttpHeaders({'Content-Type':'application/json'})
    };
 
    constructor(private httpclient:HttpClient,private router:Router,private messageService:MessageService,@Inject(BASE_PATH) private basePath:string,private encryptDecryptHelper:AESEncryptDecryptHelper){

    }

    public createPost(data:any) : Observable<any>{
        const url1=this.basePath +'post/posts/save';
        return this.httpclient.post<any>(
            url1,
            data,
            {
                headers:ServiceHelper.filesHeaders(),
               observe : 'body',
               withCredentials:true
            }
        );
    }
    public createPostWithOnlyContent(data:any) : Observable<any>{
        const url1=this.basePath +'post/content/save';
        return this.httpclient.post<any>(
            url1,
            data,
            {
                headers:ServiceHelper.filesHeaders(),
               observe : 'body',
               withCredentials:true
            }
        );
    }
    public createListing(data:any) : Observable<any>{
        const url1=this.basePath +'post/listings/save';
        return this.httpclient.post<any>(
            url1,
            data,
            {
                headers:ServiceHelper.filesHeaders(),
               observe : 'body',
               withCredentials:true
            }
        );
    }

    public getPostBySearch(searchRequest:SearchRequest) : Observable<any>{
        const url1=this.basePath +'post/posts/search';
        return this.httpclient.post<any>(
            url1,
            searchRequest,
            {
                headers:ServiceHelper.buildHeaders(),
               observe : 'body',
               withCredentials:true
            }
        );
    }
    public createAds(data:any) : Observable<any>{
        const url1=this.basePath +'post/ads';
        return this.httpclient.post<any>(
            url1,
            data,
            {
                headers:ServiceHelper.filesHeaders(),
               observe : 'body',
               withCredentials:true
            }
        );
    }
    public getPostSearchResult(searchRequest:SearchRequest) : Observable<any>{
        const url1=this.basePath +'post/getPosts';
        return this.httpclient.post<any>(
            url1,
            searchRequest,
            {
                headers:ServiceHelper.buildHeaders(),
               observe : 'body',
               withCredentials:true
            }
        );
    }
    public getSaleResultList(searchRequest:SearchRequest) : Observable<any>{
        const url1=this.basePath +'post/getlistings';
        return this.httpclient.post<any>(
            url1,
            searchRequest,
            {
                headers:ServiceHelper.buildHeaders(),
               observe : 'body',
               withCredentials:true
            }
        );
    }

    
    public getListSearchResultDet(searchRequestId:string) : Observable<any>{
        const url1=this.basePath +'post/getlisting/'+searchRequestId;
        return this.httpclient.post<any>(
            url1,
            '',
            {
                headers:ServiceHelper.buildHeaders(),
               observe : 'body',
               withCredentials:true
            }
        );
    }

    public likeOrDisLikePost(userId:string,postId:string) : Observable<any>{
        const url1=this.basePath +'post/posts/'+userId+'/'+postId+'/like';
        return this.httpclient.post<any>(
            url1,
            '',
            {
                headers:ServiceHelper.buildHeaders(),
               observe : 'body',
               withCredentials:true
            }
        );
    }


    private errorHandler(error:HttpErrorResponse){
        return of(error.message || "server error");
        
    }

    private log(message:string){
        this.messageService.add(`AuthService:${message}`,'info');
    }

    private handleError<T>( operation ='operation',result?:T){
        return (error:any):Observable<T> => {
            console.error(error);
            this.log(`${operation} failed: ${error.message}`);

            return of(result as T);
        };
    }
    
}