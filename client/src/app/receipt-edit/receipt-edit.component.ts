import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-receipt-edit',
  templateUrl: './receipt-edit.component.html',
  styleUrls: ['./receipt-edit.component.css']
})
export class ReceiptEditComponent implements OnInit {

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }

  receipt = {};

  ngOnInit() {
    let id;
    if (this.route.snapshot.params['id'] != null) {
      id = this.route.snapshot.params['id'];
    }
    else {
      id = '-1';
    }

    this.http.get('http://localhost:8080/receipt/' + id).subscribe(data => {
      this.receipt = data;
    }, (err) => {
      console.log(err);
    }
    );
  }

  saveReceipt(id) {
    if (id == null) {
      this.http.post('http://localhost:8080/receipt', [this.receipt]).subscribe(res => {
        console.log("receipt sent");
        let id = res['id'];
        this.router.navigate(['/']);
      }, (err) => {
        console.log(err);
      }
      );
    }
    else {
      this.http.put('http://localhost:8080/material/' + id, [this.receipt]).subscribe(res => {
        let id = res['id'];
        this.router.navigate(['/receipts/']);
      }, (err) => {
        console.log(err);
      });
    }
  }

}
