import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.less']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router, private tokenService:TokenService ) { }

  ngOnInit(): void {
    if(!this.tokenService.get()){
      this.router.navigate(['/'])
    }
  }

  logout(){
    this.tokenService.clear();
    this.router.navigate(['/'])
  }

}
