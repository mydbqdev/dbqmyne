import { AfterViewInit, Component, Inject, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { NavigationEnd } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { UserService } from 'src/app/common/service/user.service';
import { DefMenu } from 'src/app/common/shared/def-menu';
import { defMenuEnable } from 'src/app/common/shared/variables';
import { AuthService } from 'src/app/common/service/auth.service';
import { DataService } from 'src/app/common/service/data.service';
import { AppService } from 'src/app/common/service/application.service';
import { NotificationService } from 'src/app/common/shared/message/notification.service';
import { SearchRequest } from 'src/app/common/models/search-request.model';
import { PostSearchResult } from 'src/app/common/models/post-search-result.model';
import { PostRequestModel } from 'src/app/common/models/post-request.model';
import { SignupDetails } from 'src/app/common/shared/signup-details';
@Component({
	selector: 'app-post-search',
	templateUrl: './post.component.html',
	styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit, AfterViewInit {
	public defHomeMenu: DefMenu;
	public userNameSession: string = "";
	errorMsg: any = "";
	mySubscription: any;
	fileData: File = null;
		 previewUrl:any = null;
		 previewUrl2:any = null;
	public postSearchResult:PostSearchResult[]=[];	
	public postSearchResultDt:PostSearchResult[]=[];
	searchText:string="";	 
	//@ViewChild(SideNavMenuComponent) sidemenuComp;
	//public rolesArray: string[] = [];
	searchRequest:SearchRequest=new SearchRequest();
	files:File[]=[];
	postRequestModel:PostRequestModel=new PostRequestModel();
	 @ViewChild('closeButtonNewSave') closeButtonNewSave;
	userInfo:SignupDetails=new SignupDetails();

	@ViewChild('closeButtonNewAds') closeButtonNewAds;
	 
	business: string = '';
	adsTitle: string = '';
	adDescription: string = '';
	websiteLink:string='';
	category:string='';
	submittedAd=false;
	categories :string[] = ["Electronics","Clothing","Automotive","Real Estate","Home & Garden","Health & Beauty","Sports & Outdoors","Toys & Games","Others"];
	private window!: Window;
	currentScrolledY:number=500;
	fileDataImgAds: File = null;
	previewUrlImgAds:any[] = [];
	filesImgAds:File[]=[];
	previewUrlImg1:any = false;
	fileDataLogo: File = null;
	previewUrlLogo:any = null;	
	data: any="";
	data2: any="";
	isImg:number=0;
	load=true;
	pageIndex=1;
	isCreating = false;
	constructor( @Inject(defMenuEnable) private defMenuEnable: DefMenu,private route: ActivatedRoute, private router: Router, private http: HttpClient, private userService: UserService,
		private spinner: NgxSpinnerService, private authService:AuthService,private dataService:DataService,private appService:AppService,private notifyService: NotificationService) {
		//this.userNameSession = userService.getUsername();
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
		this.dataService.getUserDetails.subscribe(info=>{
			this.userInfo=info;
		})
		this.dataService.$postSearchResult.subscribe(dt=>{
			this.postSearchResultDt=dt;
			});
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
		this.dataService.$topSearch.subscribe(
			dt=>this.searchText=dt
		);
        this.window=window;
		this.postRequestModel.privacy= 'Anywhere';
	}
	ngAfterViewInit() {
		//this.sidemenuComp.expandMenu(1);
		//this.sidemenuComp.activeMenu(1, '');
	}

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
		this.searchResult();
		window.scrollTo(0, this.window.scrollY-100);
		this.currentScrolledY=this.window.scrollY;
		}
	}
	//test:string='';
	searchPost(event){
		this.pageIndex=0;
		this.searchText =event.target.value;
		if(this.searchText==""|| this.searchText==" "){
			this.notifyService.showWarning("Please search using valid content.", "")
			return;
		}		
		this.dataService.setTopSearch(event.target.value);
		// api post searrch
		this.postSearchResultDt=[];
		this.searchResult();
	}

	searchResult(){
		this.searchRequest.filterType='posts';
		this.searchRequest.pageIndex=this.pageIndex;
		this.searchRequest.pageSize=20;
		this.searchRequest.zipCode=this.userInfo?.zipCode;
		this.searchRequest.searchContent=this.searchText;
		this.spinner.show();
		this.appService.getPostBySearch(this.searchRequest).subscribe((data: any) => {
		 this.load=true;
		 if(data.length >0){
			
		   this.postSearchResult = Object.assign([],data);
		   for(let i of this.postSearchResult){
			this.postSearchResultDt.push(i);
		   }
		  
		 }else{
			this.pageIndex=0;
			this.searchResult();
		 }
		 this.spinner.hide();
		
		 //this.dataService.setPostSearchResult(this.postSearchResult);
		// this.router.navigateByUrl('/post-search');
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

	createPost(type:number){

		if(null == this.userInfo ){
			this.notifyService.showError("Something went wrong, please log in again.","");
			return;
		}
		this.postRequestModel.userId=this.userInfo.userId;
		this.postRequestModel.zipCode=this.userInfo.zipCode;

		if(this.files.length>0){
			this.createPostWithFile(type);
		 }else{
			this.createPostWithOutFile(type);
		 }
		 this.isCreating = true;
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
		//  this.notifyService.showSuccess(data.status, "");
		var model :PostSearchResult = Object.assign(new PostSearchResult,data) ;
		model.creatorName=this.userInfo.userFirstName +' '+this.userInfo.userLastName;
		model.likeCount=0;
		model.commentsCount=0;
		this.postSearchResultDt.unshift(model);
		  if(type==2){
			this.closeButtonNewSave.nativeElement.click();
		  }
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

	 createPostWithOutFile(type:number){
		this.spinner.show();	
		this.appService.createPostWithOnlyContent(this.postRequestModel).subscribe((data: any) => {
		 // this.notifyService.showSuccess(data.status, "");
		  var model :PostSearchResult = Object.assign(new PostSearchResult,data) ;
		  model.creatorName=this.userInfo.userFirstName +' '+this.userInfo.userLastName;
		  model.likeCount=0;
		  model.commentsCount=0;
		  this.postSearchResultDt.unshift(model);
		  this.clearPostRequestData();
		  if(type==2){
			this.closeButtonNewSave.nativeElement.click();
		  }
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
		this.postSearchResultDt[i].likeCount=this.postSearchResultDt[i].isLiked?this.postSearchResultDt[i].likeCount-1:this.postSearchResultDt[i].likeCount+1;
		this.postSearchResultDt[i].isLiked=!this.postSearchResultDt[i].isLiked;

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

	 moreContentEnable(id:number){
		 this.postSearchResultDt[id].moreContent=true;
	 }
}
