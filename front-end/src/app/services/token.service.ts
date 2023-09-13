import { Injectable } from '@angular/core';
const LOCAL_STORAGE_TOKEN_KEY = "user-token";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  get() {
    return localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)
  }
  save(token: string) {
    return localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, JSON.stringify(token))
  }
  clear(){
    localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
  }
}
