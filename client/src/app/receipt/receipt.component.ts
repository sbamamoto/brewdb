import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../user.service';

@Component({
  selector: 'app-receipt',
  templateUrl: './receipt.component.html',
  styleUrls: ['./receipt.component.css']
})
export class ReceiptComponent implements OnInit {

  receipts: any;
  edit: boolean;

  constructor(private http: HttpClient, private user:UserService) { }

  ngOnInit() {
    this.user.getUserData().subscribe( data => {
      this.edit = data.isActive
    })

    this.http.get('/api/receipt').subscribe(data => {
      this.receipts = data;
    });
  }

  deleteReceipt(id) {

      this.http.delete('/api/receipt/' + id).subscribe(data => {
        this.receipts = data;
      });

  }
}
