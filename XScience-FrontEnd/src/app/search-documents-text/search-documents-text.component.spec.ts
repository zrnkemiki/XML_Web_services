import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchDocumentsTextComponent } from './search-documents-text.component';

describe('SearchDocumentsTextComponent', () => {
  let component: SearchDocumentsTextComponent;
  let fixture: ComponentFixture<SearchDocumentsTextComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchDocumentsTextComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchDocumentsTextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
