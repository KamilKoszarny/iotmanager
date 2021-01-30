import { NgModule } from '@angular/core';
import { IotmanagerSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { HasNotAuthorityDirective } from './auth/has-not-authority.directive';

@NgModule({
  imports: [IotmanagerSharedLibsModule],
  declarations: [
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    HasNotAuthorityDirective,
  ],
  entryComponents: [LoginModalComponent],
  exports: [
    IotmanagerSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    HasNotAuthorityDirective,
  ],
})
export class IotmanagerSharedModule {}
