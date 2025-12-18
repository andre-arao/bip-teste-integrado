export interface Beneficio {
  id?: number;
  nome: string;
  descricao: string;
  valor: number;
  ativo: boolean;
  version?: number;
}
export interface TransferenciaDto {
  fromId: number;
  toId: number;
  amount: number;
}
