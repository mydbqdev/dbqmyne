import { NgModule } from '@angular/core';
import { BrowserModule, Meta, Title } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule }    from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MaterialModule } from './material.module';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgIdleKeepaliveModule } from '@ng-idle/keepalive';
import { NgxSpinnerModule } from 'ngx-spinner';
import { environment } from 'src/environments/environment';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MaskInputDirective } from './common/helpers/mask-input.directive';
import { AppAuthService } from './common/service/app-auth.service';
import { AppService } from './common/service/application.service';
import { AuthService } from './common/service/auth.service';
import { HttpInterceptorService } from './common/service/httpInterceptor.service';
import { UserService } from './common/service/user.service';
import { AlertDialogComponent } from './common/shared/alert-dialog/alert-dialog.component';
import { AlertDialogService } from './common/shared/alert-dialog/alert-dialog.service';
import { ConfirmationDialogComponent } from './common/shared/confirm-dialog/confirm-dialog.component';
import { ConfirmationDialogService } from './common/shared/confirm-dialog/confirm-dialog.service';
import { SafePipe } from './common/shared/pipe/safe-pipe';
import { ProgressBarModalComponent } from './common/shared/session-out/progressbar-modal.component';
import { BASE_PATH, defMenuEnable } from './common/shared/variables';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';
import { PageNotFoundComponent } from './components/pagenotfound/pagenotfound.component';
import { SigninComponent } from './components/signin/signin.component';
import { MainPipe } from './main-pipe.module';
import { Routes, RouterModule } from '@angular/router';
import { SignupComponent } from './components/signup/signup.component';
import { LeftPanelComponent } from './components/left-panel/left-panel.component';
import { HomeComponent } from './components/home/home.component';
import { RightPanelComponent } from './components/right-panel/right-panel.component';
import { ListingComponent } from './listing/listing/listing.component';
import { ListingLeftPanelComponent } from './listing/listing-left-panel/listing-left-panel.component';

const appRoutes: Routes = [
  {path:'**',component:PageNotFoundComponent,pathMatch:'full'}
];

@NgModule({
  declarations: [
    SafePipe,
    AppComponent,
    ConfirmationDialogComponent,
    AlertDialogComponent,
    ForbiddenComponent,
    MaskInputDirective,
    SigninComponent,
    SignupComponent,
    LeftPanelComponent,
    HomeComponent,
    RightPanelComponent,
    ListingComponent,
    ListingLeftPanelComponent
  ],
  imports: [
    RouterModule.forRoot(appRoutes,
      {useHash:true,enableTracing:true} // debugging purposes only
      ),
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    MaterialModule,
    MainPipe,
    NgxSpinnerModule.forRoot(),
    NgIdleKeepaliveModule.forRoot(),
    DragDropModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    },
    {provide:AuthService,useClass:AppAuthService},
    {provide:AppService,useClass:AppService},
    {provide:UserService,useClass:UserService},
    {provide:BASE_PATH,useValue:environment.basePath},
    {provide:defMenuEnable,useValue:environment.defMenuEnable},
    ConfirmationDialogService,AlertDialogService,
    Title,Meta
  ],
  
  bootstrap: [AppComponent],
  entryComponents: [ ConfirmationDialogComponent,AlertDialogComponent,ProgressBarModalComponent]
})
 
export class AppModule { }
declare global { interface Navigator { msSaveBlob: (blob: Blob, fileName: string) => boolean } }
