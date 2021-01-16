import { IDeviceModel } from 'app/shared/model/device-model.model';
import { ISite } from 'app/shared/model/site.model';

export interface IDevice {
  id?: number;
  serialNo?: string;
  deviceModel?: IDeviceModel;
  site?: ISite;
}

export class Device implements IDevice {
  constructor(public id?: number, public serialNo?: string, public deviceModel?: IDeviceModel, public site?: ISite) {}
}
