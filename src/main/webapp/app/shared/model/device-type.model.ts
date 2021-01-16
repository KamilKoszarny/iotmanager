import { IDeviceModel } from 'app/shared/model/device-model.model';
import { DeviceCategory } from 'app/shared/model/enumerations/device-category.model';

export interface IDeviceType {
  id?: number;
  name?: string;
  category?: DeviceCategory;
  deviceModels?: IDeviceModel[];
}

export class DeviceType implements IDeviceType {
  constructor(public id?: number, public name?: string, public category?: DeviceCategory, public deviceModels?: IDeviceModel[]) {}
}
