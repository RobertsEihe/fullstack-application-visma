import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { UserListComponent } from './user-list.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserWebclientService } from '../services/user-webclient.service';
import { environment } from 'src/environments/environment';

describe('UserListComponent', () => {
  let component: UserListComponent;
  let fixture: ComponentFixture<UserListComponent>;
  let httpMock: HttpTestingController;
  const apiUrl = environment.apiUrl;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [UserListComponent],
      providers: [UserWebclientService]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserListComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should handle successful response', () => {
    const mockUsers = [
      { id: 1, username: 'testuser1' },
      { id: 2, username: 'testuser2' }
    ];

    component.ngOnInit();
    const req = httpMock.expectOne(`${apiUrl}user`);
    req.flush(mockUsers);

    expect(component.users).toEqual(mockUsers);
    expect(component.loading).toBeFalse();
    expect(component.errorMessage).toBe('');
  });

  it('should handle HTTP error', () => {
    component.ngOnInit();
    const req = httpMock.expectOne(`${apiUrl}user`);
    req.error(new ErrorEvent('Network error'), { status: 500 });

    expect(component.users).toEqual([]);
    expect(component.loading).toBeFalse();
    expect(component.errorMessage).toContain('500');
  });

  it('should show loading state initially', () => {
    component.ngOnInit();
    expect(component.loading).toBeTrue();
    
    const req = httpMock.expectOne(`${apiUrl}user`);
    req.flush([]);
  });
});