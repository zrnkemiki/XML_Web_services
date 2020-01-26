import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentReviewsComponent } from './document-reviews.component';

describe('DocumentReviewsComponent', () => {
  let component: DocumentReviewsComponent;
  let fixture: ComponentFixture<DocumentReviewsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DocumentReviewsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentReviewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
