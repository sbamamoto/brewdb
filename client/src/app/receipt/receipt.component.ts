import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-receipt',
  templateUrl: './receipt.component.html',
  styleUrls: ['./receipt.component.css']
})
export class ReceiptComponent implements OnInit {

  receipts:any;

  constructor(private http:HttpClient, private router:Router) { }

  ngOnInit() {
    this.http.get('http://localhost:8080/receipt').subscribe( data=> {
      this.receipts = data;
      console.log("Hallo: [" +data[0].notes+"]") 
  });
  }

}
