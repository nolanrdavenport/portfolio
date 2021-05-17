`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    12:10:29 05/08/2021 
// Design Name: 
// Module Name:    ALU 
// Project Name: 
// Target Devices: 
// Tool versions: 
// Description: 
//
// Dependencies: 
//
// Revision: 
// Revision 0.01 - File Created
// Additional Comments: 
//
//////////////////////////////////////////////////////////////////////////////////
`include "parameters.v"
module ALU(
	input clk,
   input [`DATA_BUS_WIDTH-1:0] A_in,
   input [`DATA_BUS_WIDTH-1:0] B_in,
   input [3:0] alu_control,
   output reg [`DATA_BUS_WIDTH-1:0] alu_result
   );
	 
	always @ (posedge clk) begin
		case (alu_control)
			`INSTR_ADD: alu_result <= A_in + B_in; 						// add
			`INSTR_ADDI: alu_result <= A_in + B_in;
			`INSTR_SUB: alu_result <= A_in - B_in;							// sub
			`INSTR_AND: alu_result <= A_in & B_in;							// and
			`INSTR_OR: alu_result <= A_in | B_in;							// or
			`INSTR_XOR: alu_result <= A_in ^ B_in;							// xor
			`INSTR_BGEQ: begin													// geq
					if(A_in >= B_in)
						alu_result <= 1;
					else
						alu_result <= 0;
				end
			`INSTR_BLEQ: begin													// leq
					if(A_in <= B_in)
						alu_result <= 1;
					else
						alu_result <= 0;
				end
			`INSTR_BGT: begin														// gt
					if(A_in > B_in)
						alu_result <= 1;
					else
						alu_result <= 0;
				end
			`INSTR_BLT: begin														// lt
					if(A_in < B_in)
						alu_result <= 1;
					else
						alu_result <= 0;
				end
			`INSTR_BEQ: begin														// eq
					if(A_in == B_in)
						alu_result <= 1;
					else
						alu_result <= 0;
				end
			// TODO: finish the rest 0 - 9
		endcase
			
	end
	
endmodule
