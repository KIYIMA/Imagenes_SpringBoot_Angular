import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";


@Component({
  selector: 'app-select-image',
  templateUrl: './select-image.component.html',
  styleUrls: ['./select-image.component.css']
})
export class SelectImageComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
    //this.router.navigate(['']);
  }

}
