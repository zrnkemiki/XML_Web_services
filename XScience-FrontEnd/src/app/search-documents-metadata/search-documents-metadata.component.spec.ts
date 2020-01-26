import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchDocumentsMetadataComponent } from './search-documents-metadata.component';

describe('SearchDocumentsMetadataComponent', () => {
  let component: SearchDocumentsMetadataComponent;
  let fixture: ComponentFixture<SearchDocumentsMetadataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchDocumentsMetadataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchDocumentsMetadataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
