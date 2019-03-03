import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-receipt',
  templateUrl: './receipt.component.html',
  styleUrls: ['./receipt.component.css']
})
export class ReceiptComponent implements OnInit {

  receipts: any;
  edit: boolean;

  constructor(private http: HttpClient, private router: Router, private auth:AuthService) { }

  ngOnInit() {
    this.http.get('/api/receipt').subscribe(data => {
      this.receipts = data;
    });
    this.edit = this.auth.isLoggedIn
  }

  deleteReceipt(id) {
    this.http.delete('/api/receipt/' + id).subscribe(data => {
      this.receipts = data;
    });
  }
}
