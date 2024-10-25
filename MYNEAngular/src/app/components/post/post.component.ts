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
		this.dataService.$postSearchResult.subscribe(dt=>{
			this.postSearchResultDt=dt;
			console.log("this.postSearchResultDt<>< ngOnInit",this.postSearchResultDt)
			}
		);
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

		this.postRequestModel.privacy= 'Anywhere';
	}
	ngAfterViewInit() {
		//this.sidemenuComp.expandMenu(1);
		//this.sidemenuComp.activeMenu(1, '');
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

	isSaleSelect(iss:boolean){
		this.dataService.setIsSale(iss);
	  }
	searchPost(event){
       console.info("serch :"+event.target.value);
	   this.dataService.setTopSearch(event.target.value);
	   // api post searrch
	   this.searchRequest.filterType="recent";
		this.searchRequest.pageIndex=0;
		this.searchRequest.pageSize=20;
		this.searchRequest.zipCode="123456";
	   this.appService.getPostSearchResult(this.searchRequest).subscribe((data: any) => {
		console.log("data",data);
		if(data.length >0){
		  this.postSearchResult = Object.assign([],data);
		}
		console.log("this.postSearchResult>>",this.postSearchResult); 
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

	createPost(type:number){
		this.spinner.show();
		this.postRequestModel.userId=this.userInfo.userId;
		this.postRequestModel.zipCode=this.userInfo.zipCode;
		let postInfo=JSON.stringify(this.postRequestModel)	
		const formData =  new  FormData();   
		for  (var i =  0; i <  this.files.length; i++)  { 
			formData.append("files",  this.files[i]);  
		 } 
		 if(this.files.length==0){
			formData.append("files",{} as File);
		 }
		 formData.append('postInfo', postInfo );		
		this.appService.createPost(formData).subscribe((data: any) => {
		  this.notifyService.showSuccess(data, "");
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
}
