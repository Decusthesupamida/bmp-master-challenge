export type NodeType = 'START' | 'END' | 'HUMAN_TASK' | 'SERVICE_TASK' | 'GATEWAY';

export interface NodeDto {
  id: string;
  name: string;
  type: NodeType | string;
}

export interface EdgeDto {
  from: string;
  to:  string;
}

export interface DiagramDto {
  nodes: NodeDto[];
  edges: EdgeDto[];
}
