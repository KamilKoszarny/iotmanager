import { IDevice } from 'app/shared/model/device.model';

export interface ISite {
  id?: number;
  name?: string;
  addressId?: number;
  devices?: IDevice[];
}

export class Site implements ISite {
  constructor(public id?: number, public name?: string, public addressId?: number, public devices?: IDevice[]) {}
}
