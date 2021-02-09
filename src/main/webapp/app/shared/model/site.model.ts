import { IDevice } from 'app/shared/model/device.model';
import { AbstractAuditing, IAbstractAuditing } from './abstract-auditing.model';

export interface ISite extends IAbstractAuditing {
  id?: number;
  name?: string;
  city?: string;
  street?: string;
  streetNo?: string;
  devices?: IDevice[];
  userId?: number;
}

export class Site extends AbstractAuditing implements ISite {
  constructor(
    public id?: number,
    public name?: string,
    public city?: string,
    public street?: string,
    public streetNo?: string,
    public devices?: IDevice[],
    public userId?: number
  ) {
    super();
  }
}
