import { DeviceFault } from './device-fault.model';

export interface IDevice {
  id?: number;
  name?: string;
  serialNo?: string;
  modelId?: number;
  modelName?: number;
  siteId?: number;
  siteName?: number;
  faults?: DeviceFault[];
}

export class Device implements IDevice {
  constructor(public id?: number, public name?: string, public serialNo?: string, public modelId?: number, public siteId?: number) {}
}
