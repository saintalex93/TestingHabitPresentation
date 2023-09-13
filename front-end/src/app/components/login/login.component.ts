import { ResponseMessage } from './../../model/responseMessage';
import { HttpClient } from '@angular/common/http';
import { User } from './../../model/user';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/services/token.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent implements OnInit {
  user: User
  readonly apiURL: string = 'http://localhost:8070/user'

  constructor(private http: HttpClient, private router: Router, private tokenService: TokenService) {

    this.user = {
      email: '',
      password: ''
    }

  }

  ngOnInit(): void {
  }

  login() {
    this.http.post<ResponseMessage>(`${this.apiURL}/login`, this.user)
      .subscribe({
        next: (response) => {
          this.tokenService.save(response.token)
          this.router.navigate(['/home'])
        },
        error: (response) => {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: response.error.message,
          })
        }
      });
  }

}
