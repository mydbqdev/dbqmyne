import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
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
import { MediaDetails, PostSearchResult } from 'src/app/common/models/post-search-result.model';
import { PostRequestModel } from 'src/app/common/models/post-request.model';
import { SignupDetails } from 'src/app/common/shared/signup-details';

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
	 public postSearchResult:PostSearchResult[]=[];
	 searchRequest:SearchRequest=new SearchRequest();
	 postRequestModel:PostRequestModel=new PostRequestModel();
	 @ViewChild('closeButtonNewSave') closeButtonNewSave;
	 @ViewChild('closeButtonNewAds') closeButtonNewAds;
	 menuSeleced:string=''
	business: string = '';
	adsTitle: string = '';
	adDescription: string = '';
	websiteLink:string='';
	category:string='';
	submittedAd=false;
	categories :string[] = ["Electronics","Clothing","Automotive","Real Estate","Home & Garden","Health & Beauty","Sports & Outdoors","Toys & Games","Others"];
	//@ViewChild(SideNavMenuComponent) sidemenuComp;
	//public rolesArray: string[] = [];
	userInfo:SignupDetails=new SignupDetails();
	private window!: Window;
	currentScrolledY:number=2000;
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
		this.authService.checkLoginUserVlidaate();
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
	this.window=window;
	}
	ngAfterViewInit() {
		this.activeMenu('forYou');
		//this.sidemenuComp.expandMenu(1);
		//this.sidemenuComp.activeMenu(1, '');
	}
	fileDataImgAds: File = null;
	previewUrlImgAds:any[] = [];
	filesImgAds:File[]=[];
	previewUrlImg1:any = false;
	onFileChangedImg(event) {
		if(event.target.files.length>0){
		  for(let i=0;i<event.target.files.length;i++){	
		  this.fileDataImgAds=<File>event.target.files[i];
		  this.previewADs(i);
		  }
		}
	}

	previewADs(id) {
		// Show preview 
		var mimeType = this.fileDataImgAds.type;
		if (mimeType.match(/image\/*/) == null) {
		  return;
		}
		var reader = new FileReader();      
		reader.readAsDataURL(this.fileDataImgAds); 
		this.filesImgAds.push(this.fileDataImgAds);
		reader.onload = (_event) => { 
			if(id==0 && !this.previewUrlImg1){
				this.previewUrlImg1 = reader.result; 
			}else{
				this.previewUrlImgAds.push(reader.result);
			}
		}
	}
	fileDataLogo: File = null;
	previewUrlLogo:any = null;
	onFileChangedLogo(event) {
		this.previewUrlLogo=false;
		if(event.target.files.length>0){
		  this.fileDataLogo=<File>event.target.files[0];
		  var mimeType = this.fileDataLogo.type;
		  if (mimeType.match(/image\/*/) == null) {
			return;
		  }
		  var reader = new FileReader();      
		  reader.readAsDataURL(this.fileData); 
		  reader.onload = (_event) => { 
				  this.previewUrlLogo = reader.result; 	 
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
	
	isImg:number=0;
	preview(id) {
		this.isImg=0;
		// Show preview 
		var mimeType = this.fileData.type;
		//if (mimeType.match(/image\/*/) == null) {
		//  return;
		//}
		if(mimeType.match(/image\/*/)!=null && mimeType.match(/image\/*/).toString().includes("image")){
			this.isImg=1;
		}else if(mimeType.match(/video\/*/)!=null && mimeType.match(/video\/*/).toString().includes("video")){
			this.isImg=2;
		}else{
			this.isImg=0;
			return;
		}
		var reader = new FileReader();      
		reader.readAsDataURL(this.fileData); 
		this.files.push(this.fileData);
		reader.onload = (_event) => { 
			if(id==1){
		        this.previewUrl2=false;
				this.previewUrl = reader.result; 
				if(this.isImg==1){
				this.data=this.data+"&nbsp;<img src='"+this.previewUrl+"' height='140px' width='120px'  class='elevation-2'/> <button type='button' class='close' style='margin-top:-70px;cursor:pointer;'  (click)='delete(0)' aria-label='Close'><span aria-hidden='true'>&times;</span></button>";
				}else if(this.isImg==2){
					this.data=this.data+"&nbsp;<div class='video-wrapper'><video controls='true' height='140px' width='180px' loop='true' style='background-color: black;border: 1px solid gray;'><source id='video_src1' src='"+this.previewUrl+" | safe' type='"+mimeType+"'></video></div>"
				}else{

				}
			}else{
				this.previewUrl=false;
				this.previewUrl2 = reader.result; 
				if(this.isImg==1){
				this.data2=this.data2+"&nbsp;<img src='"+this.previewUrl2+"' height='140px' width='120px'  class='elevation-2'/>";
			    }else if(this.isImg==2){
				this.data2=this.data2+"&nbsp;<div class='video-wrapper'><video controls='true' height='140px' width='180px' loop='true' style='background-color: black;border: 1px solid gray;'><source id='video_src1' src='"+this.previewUrl+" | safe' type='"+mimeType+"'></video></div>"
			    }else{

			    }
			}
		}
	}
    isSaleSelect(iss:boolean){
      this.dataService.setIsSale(iss);
	}

	loadMoreProducts(){
		if(this.load){
		this.load=false;
		this.pageIndex=this.pageIndex+1;
		this.searchPostForHome(this.menuSeleced);
		window.scrollTo(0, this.currentScrolledY);
		this.currentScrolledY=this.window.scrollY;
		}
	}

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
		   this.postSearchResult = Object.assign(data);
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

		if(this.postRequestModel.description ==undefined )
		{
			this.postRequestModel.description ="";
		}

		if(this.files.length>0){
			this.createPostWithFile(type);
		 }else{
			this.createPostWithOutFile(type);
		 }
		 this.isCreating = true;

	}
	clearPostRequestData(){
		this.isCreating = false;
		this.postRequestModel.description="";
		this.previewUrl2=null;
		this.previewUrl=null;
		this.data2="";
		this.data="";
		this.postRequestModel.privacy="Anywhere";
		this.files=[];
		
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
		 // this.notifyService.showSuccess(data.status, "");
		  var model :PostSearchResult = Object.assign(new PostSearchResult,data) ;
		  this.postSearchResult.unshift(model);
		  this.clearPostRequestData();
		  if(type==2){
			this.closeButtonNewSave.nativeElement.click();
		  }
		  this.isCreating = false;
		 this.spinner.hide();
	   },error =>{
		 this.isCreating = false;
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
		 // this.notifyService.showSuccess(data.status, "");
		 var model :PostSearchResult = Object.assign(new PostSearchResult,data) ;
		 this.postSearchResult.unshift(model);
		  this.clearPostRequestData();
		  if(type==2){
			this.closeButtonNewSave.nativeElement.click();
		  }
		  this.isCreating = false;
		 this.spinner.hide();
	   },error =>{
		this.isCreating = false;
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
	 activeMenu(menuName:string){
		this.postSearchResult=[];
		this.pageIndex=0;
		this.menuSeleced=menuName;
		this.searchPostForHome(this.menuSeleced);
	 }

	 load=true;
	 pageIndex=0;
	 postSearchResultResponce:PostSearchResult[]=[];
	 isCreating = false;
	 searchPostForHome(filterType:string){
		// api post searrch
		this.spinner.show();
		this.searchRequest.filterType=filterType;
		this.searchRequest.pageIndex=this.pageIndex;
		this.searchRequest.pageSize=20;
		
		this.searchRequest.zipCode="123456";
		this.appService.getPostSearchResult(this.searchRequest).subscribe((data: any) => {
			this.load=true;
			if(data.length >0){
			this.postSearchResultResponce=Object.assign([],data);
			for(let i of this.postSearchResultResponce){
				this.postSearchResult.push(i);
			}
		 }else{
			this.pageIndex=0;
			this.searchPostForHome(filterType);
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
		if(this.filesImgAds.length==0){
			this.notifyService.showError("Please choose atleast one image.", "")
			return;
		}
		this.submittedAd=true; 
		if (this.business !== '' && this.business !== null && this.adsTitle !== '' && this.adsTitle !== null && this.adsTitle !== '' && this.adsTitle !== null &&
			this.adDescription !== '' && this.adDescription !== null && this.category !== '' && this.category !== null&& this.websiteLink !== '' && this.websiteLink !== null)
			 {
				this.spinner.show();
				let adsRequestModel=new PostSearchResult();
				adsRequestModel.title = this.adsTitle ;
				adsRequestModel.description = this.adDescription ;
				adsRequestModel.privacy = "Anywhere" ;
				adsRequestModel.hyperLink = this.websiteLink!=null? this.websiteLink: "" ;
				adsRequestModel.userId = this.userInfo.userId;
				adsRequestModel.description = this.adDescription ;
				adsRequestModel.category = this.category;
		
				const formData =  new  FormData();   
						for  (var i =  0; i <  this.filesImgAds.length; i++)  { 
						 formData.append("files",  this.filesImgAds[i]);  
					   } 
				let adsInfo=JSON.stringify(adsRequestModel);	
				
				formData.append('adsInfo', adsInfo );	

		this.isCreating = true;
		this.appService.createAds(formData).subscribe((data: any) => {
		  this.notifyService.showSuccess(data.status, "");
		  this.closeButtonNewAds.nativeElement.click();
		 this.spinner.hide();
		 this.isCreating = false;
	   },error =>{
		this.isCreating = false;
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
	 }

	 resetAdPopup(){
		 this.isCreating=false;
		 this.previewUrlImg1=false;
		 this.filesImgAds=[];
		 this.previewUrlImgAds=[];
		 this.business="";
		 this.adDescription="";
		 this.adsTitle="";
		 this.websiteLink="";
	 }

	 
	 likeOrDisLikePost(post:any,i:number){

		let userId=this.userInfo.userId;
		let postId=post.postId;
	    this.appService.likeOrDisLikePost(userId,postId).subscribe((data: any) => {
		this.postSearchResult[i].likeCount=this.postSearchResult[i].isLiked?this.postSearchResult[i].likeCount-1:this.postSearchResult[i].likeCount+1;
		this.postSearchResult[i].isLiked=!this.postSearchResult[i].isLiked;

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
	 mediaPathsSlide:MediaDetails[]=[];
     slidesPhoto(list:any[]){
		this.mediaPathsSlide=Object.assign([],list);
		console.info("this.mediaPathsSlide",this.mediaPathsSlide);
	 }
	 moreContentEnable(id:number){
		this.postSearchResult[id].moreContent=true;
	}
}
