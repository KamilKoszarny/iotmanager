import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDeviceProducer } from 'app/shared/model/device-producer.model';

type EntityResponseType = HttpResponse<IDeviceProducer>;
type EntityArrayResponseType = HttpResponse<IDeviceProducer[]>;

@Injectable({ providedIn: 'root' })
export class DeviceProducerService {
  public resourceUrl = SERVER_API_URL + 'api/device-producers';

  constructor(protected http: HttpClient) {}

  create(deviceProducer: IDeviceProducer): Observable<EntityResponseType> {
    return this.http.post<IDeviceProducer>(this.resourceUrl, deviceProducer, { observe: 'response' });
  }

  update(deviceProducer: IDeviceProducer): Observable<EntityResponseType> {
    return this.http.put<IDeviceProducer>(this.resourceUrl, deviceProducer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDeviceProducer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeviceProducer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
