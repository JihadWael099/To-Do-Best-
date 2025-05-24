import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { appConfig } from './app/app.config'; 
import { AppComponent } from './app/app.component'; 

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withFetch()),  
    ...appConfig.providers  
  ]
})
  .catch((err) => console.error(err));
