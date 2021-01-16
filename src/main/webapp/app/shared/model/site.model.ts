import { IAddress } from 'app/shared/model/address.model';
import { IDevice } from 'app/shared/model/device.model';

export interface ISite {
  id?: number;
  name?: string;
  address?: IAddress;
  devices?: IDevice[];
}

export class Site implements ISite {
  constructor(public id?: number, public name?: string, public address?: IAddress, public devices?: IDevice[]) {}
}
