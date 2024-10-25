import { AfterViewInit, Component, Inject, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { NavigationEnd } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { UserService } from 'src/app/common/service/user.service';
import { AuthService } from 'src/app/common/service/auth.service';
import { DataService } from 'src/app/common/service/data.service';
//import { tap, switchMap, scan } from 'rxjs/dist/types/operators';
import {BehaviorSubject,Observable} from 'rxjs';
import { SearchRequest } from 'src/app/common/models/search-request.model';
import { AppService } from 'src/app/common/service/application.service';
import { NotificationService } from 'src/app/common/shared/message/notification.service';
import { PostSearchResult } from 'src/app/common/models/post-search-result.model';
import { PostRequestModel } from 'src/app/common/models/post-request.model';
import { SignupDetails } from 'src/app/common/shared/signup-details';

export interface DummyJsonResponse {
	products: Product[];
	total: number;
	skip: number;
	limit: number;
  }
  
  export interface Product {
	id: string;
	title: string;
	price: number;
	thumbnail: string;
  }
  
  export interface ProductsPaginator {
	items: Product[];
	page: number;
	hasMorePages: boolean;
  }
@Component({
	selector: 'app-home',
	templateUrl: './home.component.html',
	styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, AfterViewInit {
	public userNameSession: string = "";
	errorMsg: any = "";
	mySubscription: any;
	fileData: File = null;
		 previewUrl:any = null;
		 previewUrl2:any = null;
    files:File[]=[];
     public paginator$: Observable<ProductsPaginator>;
     public loading$ = new BehaviorSubject(true);
	 private page$ = new BehaviorSubject(1);
	 public postSearchResult:PostSearchResult[]=[];
	 searchRequest:SearchRequest=new SearchRequest();
	 postRequestModel:PostRequestModel=new PostRequestModel();
	 @ViewChild('closeButtonNewSave') closeButtonNewSave;

	 
	business: string = '';
	adstitle: string = '';
	adDescription: string = '';
	submittedAd=false;

	//@ViewChild(SideNavMenuComponent) sidemenuComp;
	//public rolesArray: string[] = [];
	userInfo:SignupDetails=new SignupDetails();
	constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, private userService: UserService,
		private spinner: NgxSpinnerService, private authService:AuthService,private dataService:DataService,private appService:AppService,private notifyService: NotificationService) {
		this.userNameSession = userService.getUsername();
		//this.defHomeMenu=defMenuEnable;
		//if (userService.getUserinfo() != undefined) {
		//	this.rolesArray = userService.getUserinfo().previlageList;
		//}
		this.router.routeReuseStrategy.shouldReuseRoute = function () {
			return false;
		};

		this.mySubscription = this.router.events.subscribe((event) => {
			if (event instanceof NavigationEnd) {
				// Trick the Router into believing it's last link wasn't previously loaded
				this.router.navigated = false;
			}
		});
		//this.paginator$ = this.loadProducts$();
	}

	ngOnDestroy() {
		if (this.mySubscription) {
			this.mySubscription.unsubscribe();
		}
	}
	ngOnInit() {
		//if (this.userNameSession == null || this.userNameSession == undefined || this.userNameSession == '') {
		//	this.router.navigate(['/']);
		//}
		this.dataService.getUserDetails.subscribe(info=>{
			this.userInfo=info;
		}
		)
	 this.postRequestModel.privacy= 'Anywhere';
	 this.searchPostForHome();
	}
	ngAfterViewInit() {
		//this.sidemenuComp.expandMenu(1);
		//this.sidemenuComp.activeMenu(1, '');
	}
	onFileChangedImg(event) {
		this.previewUrl=false;
		this.files=[];
		this.data="";
		if(event.target.files.length>0){
		  for(let i=0;i<event.target.files.length;i++){	
		  this.fileData=<File>event.target.files[i];
		  //this.preview(id);
		  }
		}
	
	}

	onFileChangedLogo(event) {
		this.previewUrl=false;
		this.files=[];
		this.data="";
		if(event.target.files.length>0){
		  for(let i=0;i<event.target.files.length;i++){	
		  this.fileData=<File>event.target.files[i];
		  //this.preview(id);
		  }
		}
	
	}
	onFileChanged(event,id:number) {
		this.files=[];
		this.previewUrl=false;
		this.previewUrl2=false;
		this.data="";
		this.data2="";
		if(event.target.files.length>0){
		  for(let i=0;i<event.target.files.length;i++){	
		  this.fileData=<File>event.target.files[i];
		  this.preview(id);
		  }
		}
	
	}
	data: any="";
	data2: any="";
	
	preview(id) {
		// Show preview 
		var mimeType = this.fileData.type;
		if (mimeType.match(/image\/*/) == null) {
		  return;
		}
		var reader = new FileReader();      
		reader.readAsDataURL(this.fileData); 
		this.files.push(this.fileData);
		reader.onload = (_event) => { 
			if(id==1){
		        this.previewUrl2=false;
				this.previewUrl = reader.result; 
				this.data=this.data+"&nbsp;<img src='"+this.previewUrl+"' height='140px' width='120px'  class='elevation-2'/> <button type='button' class='close' style='margin-top:-70px;cursor:pointer;'  (click)='delete(0)' aria-label='Close'><span aria-hidden='true'>&times;</span></button>";
			}else{
				this.previewUrl=false;
				this.previewUrl2 = reader.result; 
				this.data2=this.data2+"&nbsp;<img src='"+this.previewUrl2+"' height='140px' width='120px'  class='elevation-2'/>";
			}
		}
	}
    isSaleSelect(iss:boolean){
      this.dataService.setIsSale(iss);
	}

	loadMoreProducts(paginator: ProductsPaginator){
		console.info("scrolling down");
		//if (!paginator.hasMorePages) {
		//	return;
		//  }
		//  this.page$.next(paginator.page + 1);
	}

    /*private loadProducts$(): Observable<ProductsPaginator> {
		return this.page$.pipe(
		  tap(() => this.loading$.next(true)),
		   switchMap((page) => this.api.getProducts$(page)),
		  scan(this.updatePaginator, {items: [], page: 0, hasMorePages: true} as ProductsPaginator),
		  tap(() => this.loading$.next(false)),
		);
	  }
	
	  private updatePaginator(accumulator: ProductsPaginator, value: ProductsPaginator): ProductsPaginator {
		if (value.page === 1) {
		  return value;
		}
	
		accumulator.items.push(...value.items);
		accumulator.page = value.page;
		accumulator.hasMorePages = value.hasMorePages;
	
		return accumulator;
	  }*/

	  searchPost(event){

		let test:string=event.target.value;
		if(test==""|| test==" "){
			this.notifyService.showWarning("Please search using valid content.", "")
			return;
		}
		this.spinner.show();
		
		this.dataService.setTopSearch(event.target.value);
		// api post searrch
		this.searchRequest.filterType='posts';
		this.searchRequest.pageIndex=0;
		this.searchRequest.pageSize=20;
		this.searchRequest.zipCode="123456";
		this.searchRequest.searchContent=test;
		this.appService.getPostBySearch(this.searchRequest).subscribe((data: any) => {
		 if(data.length >0){
		   this.postSearchResult = Object.assign([],data);
		 }else{
			this.postSearchResult = Object.assign([]);
		 }
		 this.spinner.hide();
		 this.dataService.setPostSearchResult(this.postSearchResult);
		 this.router.navigateByUrl('/post-search');
	   },error =>{
		 this.spinner.hide();
		 if(error.status==403){
		   this.router.navigate(['/forbidden']);
		 }else  if (error.error && error.error.message) {
		   this.errorMsg =error.error.message;
		   console.log("Error:"+this.errorMsg);
		   this.notifyService.showError(this.errorMsg, "");
		   this.spinner.hide();
		 } else {
		   this.spinner.hide();
		   if(error.status==500 && error.statusText=="Internal Server Error"){
			 this.errorMsg=error.statusText+"! Please login again or contact your Help Desk.";
		   }else{
			 let str;
			   if(error.status==400){
			   str=error.error;
			   }else{
				 str=error.message;
				 str=str.substring(str.indexOf(":")+1);
			   }
			   console.log("Error:"+str);
			   this.errorMsg=str;
		   }
		   if(error.status !== 401 ){this.notifyService.showError(this.errorMsg, "");}
		 }
	   });
		
	 }

	delete(id:number){
		alert("delete");
	}

	createPost(type:number){

		if(null == this.userInfo ){
			this.notifyService.showError("Something went wrong, please log in again.","");
			return;
		}
		this.postRequestModel.userId=this.userInfo.userId;
		this.postRequestModel.zipCode=this.userInfo.zipCode;

		if(this.postRequestModel.description ==undefined ||this.postRequestModel.description == "" || "" == this.postRequestModel?.description.trim())
		{
			this.notifyService.showError("Please provide some description. ","");
			return;
		}

		if(this.files.length>0){
			this.createPostWithFile(type);
		 }else{
			this.createPostWithOutFile(type);
		 }

	}
	
	createPostWithFile(type:number){
		this.spinner.show();
		let postInfo=JSON.stringify(this.postRequestModel)	
		const formData =  new  FormData();   
		for  (var i =  0; i <  this.files.length; i++)  { 
			formData.append("files",  this.files[i]);  
		 } 
		
		 formData.append('postInfo', postInfo );		
		this.appService.createPost(formData).subscribe((data: any) => {
		  this.notifyService.showSuccess(data.status, "");
		  this.postRequestModel.description="";
		  this.previewUrl2=null;
		  this.previewUrl=null;
		  this.data2="";
		  this.data="";
		  this.postRequestModel.privacy="";
		  if(type==2){
			this.closeButtonNewSave.nativeElement.click();
		  }
		 this.spinner.hide();
	   },error =>{
		 this.spinner.hide();
		 if(error.status==403){
		   this.router.navigate(['/forbidden']);
		 }else  if (error.error && error.error.message) {
		   this.errorMsg =error.error.message;
		   console.log("Error:"+this.errorMsg);
		   this.notifyService.showError(this.errorMsg, "");
		   this.spinner.hide();
		 } else {
		   this.spinner.hide();
		   if(error.status==500 && error.statusText=="Internal Server Error"){
			 this.errorMsg=error.statusText+"! Please login again or contact your Help Desk.";
		   }else{
			 let str;
			   if(error.status==400){
			   str=error.error;
			   }else{
				 str=error.message;
				 str=str.substring(str.indexOf(":")+1);
			   }
			   console.log("Error:"+str);
			   this.errorMsg=str;
		   }
		   if(error.status !== 401 ){this.notifyService.showError(this.errorMsg, "");}
		 }
	   });
		
	 }

	 createPostWithOutFile(type:number){
		this.spinner.show();	
		this.appService.createPostWithOnlyContent(this.postRequestModel).subscribe((data: any) => {
		  this.notifyService.showSuccess(data.status, "");
		  this.postRequestModel.description="";
		  this.previewUrl2=null;
		  this.previewUrl=null;
		  this.data2="";
		  this.data="";
		  this.postRequestModel.privacy="";
		  if(type==2){
			this.closeButtonNewSave.nativeElement.click();
		  }
		 this.spinner.hide();
	   },error =>{
		 this.spinner.hide();
		 if(error.status==403){
		   this.router.navigate(['/forbidden']);
		 }else  if (error.error && error.error.message) {
		   this.errorMsg =error.error.message;
		   console.log("Error:"+this.errorMsg);
		   this.notifyService.showError(this.errorMsg, "");
		   this.spinner.hide();
		 } else {
		   this.spinner.hide();
		   if(error.status==500 && error.statusText=="Internal Server Error"){
			 this.errorMsg=error.statusText+"! Please login again or contact your Help Desk.";
		   }else{
			 let str;
			   if(error.status==400){
			   str=error.error;
			   }else{
				 str=error.message;
				 str=str.substring(str.indexOf(":")+1);
			   }
			   console.log("Error:"+str);
			   this.errorMsg=str;
		   }
		   if(error.status !== 401 ){this.notifyService.showError(this.errorMsg, "");}
		 }
	   });
		
	 }

	 searchPostForHome(){
		// api post searrch
		this.spinner.show();
		this.searchRequest.filterType="foryou";
		this.searchRequest.pageIndex=0;
		this.searchRequest.pageSize=20;
		this.searchRequest.zipCode="123456";
		this.appService.getPostSearchResult(this.searchRequest).subscribe((data: any) => {
		 if(data.length >0){
		   this.postSearchResult = Object.assign([],data);
		 }
		 this.spinner.hide();
	   },error =>{
		 this.spinner.hide();
		 if(error.status==403){
		   this.router.navigate(['/forbidden']);
		 }else  if (error.error && error.error.message) {
		   this.errorMsg =error.error.message;
		   console.log("Error:"+this.errorMsg);
		   this.notifyService.showError(this.errorMsg, "");
		   this.spinner.hide();
		 } else {
		   this.spinner.hide();
		   if(error.status==500 && error.statusText=="Internal Server Error"){
			 this.errorMsg=error.statusText+"! Please login again or contact your Help Desk.";
		   }else{
			 let str;
			   if(error.status==400){
			   str=error.error;
			   }else{
				 str=error.message;
				 str=str.substring(str.indexOf(":")+1);
			   }
			   console.log("Error:"+str);
			   this.errorMsg=str;
		   }
		   if(error.status !== 401 ){this.notifyService.showError(this.errorMsg, "");}
		 }
	   });
		
	 }

	 newAd(){
		this.submittedAd=true; 
	 }
}
