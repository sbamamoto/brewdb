import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BrewerComponent } from './brewer.component';

describe('BrewerComponent', () => {
  let component: BrewerComponent;
  let fixture: ComponentFixture<BrewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BrewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BrewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
