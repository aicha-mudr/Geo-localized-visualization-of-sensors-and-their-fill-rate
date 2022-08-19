/* eslint-disable*/
import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import * as $ from 'jquery'

import {LoginService} from "app/core/login/login.service";
import {Router} from "@angular/router";
import {FormBuilder, Validators} from "@angular/forms";
import {AccountService} from "../core/auth/account.service";
import {RegisterService} from "../account/register/register.service";
import {HttpErrorResponse} from "@angular/common/http";
import {EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE} from "../shared/constants/error.constants";

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  @ViewChild('username', { static: false })
  username?: ElementRef;

  authenticationError = false;


  doNotMatch = false;
  error = false;
  errorEmailExists = false;
  errorUserExists = false;
  success = false;

  loginForm = this.fb.group({
    username: [''],
    password: [''],
    rememberMe: [false],
  });

  registerForm = this.fb.group({
    login: [
      '',
      [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    ],
    email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
  });


  constructor(private loginService: LoginService,
              private accountService:AccountService,
              private router: Router, private fb: FormBuilder,
              private registerService: RegisterService
  ) {}

  ngAfterViewInit(): void {
    if (this.username) {
      this.username.nativeElement.focus();
    }
  }

  cancel(): void {
    this.authenticationError = false;
    this.loginForm.patchValue({
      username: '',
      password: '',
    });
  }
  connecter(): void {
    this.loginService
      .login({
        username: this.loginForm.get('username')!.value,
        password: this.loginForm.get('password')!.value,
        rememberMe: this.loginForm.get('rememberMe')!.value,
      })
      .subscribe(
        () => {
          this.authenticationError = false;
          if (
            this.router.url === '/account/register' ||
            this.router.url.startsWith('/account/activate') ||
            this.router.url.startsWith('/account/reset/')
          ) {
            this.router.navigate(['']);
          }
          this.router.navigate(['/Dashboard']);
        },
        () => (this.authenticationError = true)
      );
  }


  requestResetPassword(): void {
    this.router.navigate(['/account/reset', 'request']);
  }

  onSwitch(classToremove:string,classToadd:string) {
    $(classToremove).removeClass('switched');
    $(classToadd).addClass('switched');
    this.doNotMatch = false;
    this.error = false;
    this.errorEmailExists = false;
    this.errorUserExists = false;
    this.success = false;
    this.registerForm.reset();

  }
  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  ngOnInit(): void {
    if (this.isAuthenticated()) {
      this.router.navigate(['/home']);
    }
  }

  register(): void {
    this.doNotMatch = false;
    this.error = false;
    this.errorEmailExists = false;
    this.errorUserExists = false;

    const password = this.registerForm.get(['password'])!.value;
    if (password !== this.registerForm.get(['confirmPassword'])!.value) {
      this.doNotMatch = true;
    } else {
      const login = this.registerForm.get(['login'])!.value;
      const email = this.registerForm.get(['email'])!.value;
      this.registerService.save({ login, email, password, langKey: 'en' }).subscribe(
        () => {
          this.success = true;
          this.registerForm.reset();
        },
        response => this.processError(response)
      );
    }
  }

  private processError(response: HttpErrorResponse): void {
    if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
      this.errorUserExists = true;
    } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
      this.errorEmailExists = true;
    } else {
      this.error = true;
    }
  }
}
