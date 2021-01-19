export interface IDevice {
  id?: number;
  name?: string;
  serialNo?: string;
  deviceModelId?: number;
  siteId?: number;
}

export class Device implements IDevice {
  constructor(public id?: number, public name?: string, public serialNo?: string, public deviceModelId?: number, public siteId?: number) {}
}
