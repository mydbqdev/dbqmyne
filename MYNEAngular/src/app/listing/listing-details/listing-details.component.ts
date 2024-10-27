import { AfterViewInit, Component, Inject, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { NavigationEnd } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { UserService } from 'src/app/common/service/user.service';
import { AuthService } from 'src/app/common/service/auth.service';
import { DataService } from 'src/app/common/service/data.service';
import { SearchRequest } from 'src/app/common/models/search-request.model';
import { AppService } from 'src/app/common/service/application.service';
import { NotificationService } from 'src/app/common/shared/message/notification.service';
import { PostSearchResult } from 'src/app/common/models/post-search-result.model';
import { PostRequestModel } from 'src/app/common/models/post-request.model';
import { SignupDetails } from 'src/app/common/shared/signup-details';

@Component({
	selector: 'app-listing-details',
	templateUrl: './listing-details.component.html',
	styleUrls: ['./listing-details.component.css']
})
export class ListingDetailsComponent implements OnInit, AfterViewInit {
	public userNameSession: string = "";
	errorMsg: any = "";
	mySubscription: any;
	fileData: File = null;
	previewUrl:any = null;
	previewUrl2:any = null;
	isSaleSelect:boolean=true;
	listingId:string;
	//@ViewChild(SideNavMenuComponent) sidemenuComp;
	//public rolesArray: string[] = [];
	public postSearchResult:PostSearchResult[]=[];
	public searchResultDet:PostSearchResult=new PostSearchResult();
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
	categories :string[] = ["Electronics","Clothing","Automotive","Real Estate","Home & Garden","Health & Beauty","Sports & Outdoors","Toys & Games",
		"Books","Fashion Accessories","Furniture","Jewelry","Pet Supplies","Musical Instruments","Computers & Tablets","Cell Phones","Tickets & Experiences","Collectibles","Office Supplies","Others"];

	constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, private userService: UserService,
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
		this.dataService.getIsSale.subscribe(
			isS=>this.isSaleSelect=isS
		);
		this.route.params.subscribe(s => {		
			this.listingId=s["id"];
			this.searchListingDet()
		  });
		  this.postRequestModel.privacy= 'Anywhere';	  
	}
	ngAfterViewInit() {
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
				this.data=this.data+"&nbsp;<img src='"+this.previewUrl+"' height='140px' width='120px'  class='elevation-2'/>";
			}else{
				this.previewUrl=false;
				this.previewUrl2 = reader.result; 
				this.data2=this.data2+"&nbsp;<img src='"+this.previewUrl2+"' height='140px' width='120px'  class='elevation-2'/>";
			}
		}
	}
	isSalSelect(iss:boolean){
		this.dataService.setIsSale(iss);
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


	 searchListingDet(){
		this.appService.getListSearchResultDet(this.listingId).subscribe((data: any) => {
		   this.searchResultDet =data;
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
	 clearPostRequestData(){
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
		  this.clearPostRequestData();
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

	 newAd(){
		if(this.filesImgAds.length==0){
			this.notifyService.showError("Please choose atleast one image.", "")
			return;
		}
		this.submittedAd=true; 
		if (this.business !== '' && this.business !== null && this.adsTitle !== '' && this.adsTitle !== null &&
			this.adDescription !== '' && this.adDescription !== null)
			 {
				this.spinner.show();
				const formData =  new  FormData();   
		        for  (var i =  0; i <  this.filesImgAds.length; i++)  { 
			     formData.append("files",  this.filesImgAds[i]);  
		       } 
		
		 formData.append('business', this.business );	
		 formData.append('adsTitle', this.adsTitle );	
		 formData.append('adDescription', this.adDescription );	
		 formData.append('category', this.category );
		 formData.append('websiteLink', this.websiteLink!=null? this.websiteLink: "");	

		this.appService.createPost(formData).subscribe((data: any) => {
		  this.notifyService.showSuccess(data.status, "");
		  this.closeButtonNewAds.nativeElement.click();
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
	 }

	 resetAdPopup(){
		 this.previewUrlImg1=false;
		 this.filesImgAds=[];
		 this.previewUrlImgAds=[];
		 this.business="";
		 this.adDescription="";
		 this.adsTitle="";
		 this.websiteLink="";
	 }
}
