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

     public paginator$: Observable<ProductsPaginator>;
     public loading$ = new BehaviorSubject(true);
     private page$ = new BehaviorSubject(1);
	//@ViewChild(SideNavMenuComponent) sidemenuComp;
	//public rolesArray: string[] = [];

	constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, private userService: UserService,
		private spinner: NgxSpinnerService, private authService:AuthService,private dataService:DataService) {
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
	searchPost(){
		this.router.navigateByUrl('/post-search');
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



	delete(id:number){
		alert("delete");
	}
}
