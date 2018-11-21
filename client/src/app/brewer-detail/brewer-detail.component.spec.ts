import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BrewerDetailComponent } from './brewer-detail.component';

describe('BrewerDetailComponent', () => {
  let component: BrewerDetailComponent;
  let fixture: ComponentFixture<BrewerDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BrewerDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BrewerDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
