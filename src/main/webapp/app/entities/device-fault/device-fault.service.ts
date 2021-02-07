import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDeviceFault } from 'app/shared/model/device-fault.model';

type EntityResponseType = HttpResponse<IDeviceFault>;
type EntityArrayResponseType = HttpResponse<IDeviceFault[]>;

@Injectable({ providedIn: 'root' })
export class DeviceFaultService {
  public resourceUrl = SERVER_API_URL + 'api/device-faults';

  constructor(protected http: HttpClient) {}

  create(deviceFault: IDeviceFault): Observable<EntityResponseType> {
    return this.http.post<IDeviceFault>(this.resourceUrl, deviceFault, { observe: 'response' });
  }

  update(deviceFault: IDeviceFault): Observable<EntityResponseType> {
    return this.http.put<IDeviceFault>(this.resourceUrl, deviceFault, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDeviceFault>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeviceFault[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
