export class AuditTrailModel{
    id: number;
    user_id: number;
    username: string;
    action: string;
    timestamp: Date;
    details: Text;
}