import { TestBed } from '@angular/core/testing';

import { ReviewServiceService } from './review-service.service';

describe('ReviewServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ReviewServiceService = TestBed.get(ReviewServiceService);
    expect(service).toBeTruthy();
  });
});
