import { DeviceFault } from './device-fault.model';
import { AbstractAuditing, IAbstractAuditing } from './abstract-auditing.model';

export interface IDevice extends IAbstractAuditing {
  id?: number;
  name?: string;
  serialNo?: string;
  modelId?: number;
  modelName?: number;
  siteId?: number;
  siteName?: number;
  faults?: DeviceFault[];
}

export class Device extends AbstractAuditing implements IDevice {
  constructor(public id?: number, public name?: string, public serialNo?: string, public modelId?: number, public siteId?: number) {
    super();
  }
}
