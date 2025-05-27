import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { appConfig } from './app/app.config'; 
import { AppComponent } from './app/app.component'; 
import { authInterceptor } from './app/service/auth.interceptor'; 

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withInterceptors([authInterceptor])),  
    ...appConfig.providers  
  ]
})
.catch((err) => console.error(err));
