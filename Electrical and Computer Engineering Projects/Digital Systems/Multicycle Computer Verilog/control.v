`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    21:52:53 05/08/2021 
// Design Name: 
// Module Name:    control 
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
module control(
	input program_mode,
   input clk,
	input reset,
   input [3:0] opcode,
	input result_lsb,
   output reg ir_enable,
   output reg dmem_read,
   output reg dmem_write,
	output reg imem_read,
	output reg imem_write,
	output reg pc_increment,
	output reg alu_reg_enable,
	output reg pc_enable,
	output reg alu_src_B,
	output reg alu_out_reg_enable,
	output reg dmem_out_reg_enable,
	output reg reg_write_enable,
	output reg [1:0] select_reg_write_data
   );
	
	
	reg [`NUM_STATE_BITS-1:0] curr_state;
	wire [`NUM_STATE_BITS-1:0] next_state;
	
	assign next_state = get_next_state(curr_state, opcode, reset);
	
	function [`NUM_STATE_BITS-1:0] get_next_state;
		input [`NUM_STATE_BITS-1:0] curr_state;
		input [3:0] opcode;
		input reset;
		
		
			case (curr_state)
				`BOOT: begin
					get_next_state = `FETCH;
				end
				`FETCH: begin
					get_next_state = `DECODE;
				end
				`DECODE: begin
					if(opcode == `INSTR_BLEQ || opcode == `INSTR_ADD) begin
						get_next_state = `AOPB;
					end else if(opcode == `INSTR_LW) begin
						get_next_state = `LOADW;
					end else if(opcode == `INSTR_LI) begin
						get_next_state = `LOADI;
					end else if(opcode == `INSTR_SW) begin
						get_next_state = `STOREW;
					end else if(opcode == `INSTR_ADDI) begin
						get_next_state = `AOPIMM;
					end else if(opcode == `INSTR_NOP) begin
						get_next_state = `FETCH;
					end
				end
				`AOPB: begin
					if(opcode == `INSTR_BLEQ) begin
						get_next_state = `BRANCH;
					end else if(opcode == `INSTR_ADD) begin
						get_next_state = `RTYPE;
					end
				end

				`AOPIMM: begin
					get_next_state = `RTYPE;
				end

				`LOADW: begin
					get_next_state = `FETCH;
				end

				`LOADI: begin
					get_next_state = `FETCH;
				end

				`STOREW: begin
					get_next_state = `FETCH;
				end

				`BRANCH: begin
					get_next_state = `FETCH;
				end

				`RTYPE: begin
					get_next_state = `FETCH;
				end
			endcase
	endfunction
	
	always @ (posedge clk) begin
		if(reset == 1) begin
			curr_state <= `BOOT;
		end else begin
			curr_state <= next_state; 
			case (next_state)
			`FETCH: begin
				ir_enable 	<= 1'b1;//fetch from instruction reg
				dmem_read 	<= 1'b0;
				dmem_write 	<= 1'b0;
				imem_read 	<= 1'b1;//read M[PC]
				imem_write 	<= 1'b0;
				pc_increment <= 1'b0;
				alu_reg_enable		<= 1'b0;
				pc_enable			<= 1'b0;
				alu_src_B			<= 1'b0;
				alu_out_reg_enable		<= 1'b0;
				dmem_out_reg_enable		<= 1'b0;
				reg_write_enable			<= 1'b0;
				select_reg_write_data	<= 2'b00;
			end
			`DECODE: begin
				ir_enable 	<= 1'b0;
				dmem_write 	<= 1'b0;
				imem_read 	<= 1'b0;
				imem_write 	<= 1'b0;
				pc_increment		<= 1'b1;//pc = pc + 1
				alu_reg_enable		<= 1'b1;//set Ain and Bin registers
				pc_enable			<= 1'b0;
				alu_src_B			<= 1'b0;
				alu_out_reg_enable		<= 1'b0;
				if(opcode == `INSTR_LW) begin // if load word
					dmem_out_reg_enable	<= 1'b1;
					dmem_read 				<= 1'b1;
				end else begin 
					dmem_out_reg_enable 	<= 1'b0;
					dmem_read 				<= 1'b0;
				end
				reg_write_enable			<= 1'b0;
				select_reg_write_data	<= 2'b00;
			end
			`AOPB: begin
				ir_enable 	<= 1'b0;
				dmem_read 	<= 1'b0;
				dmem_write 	<= 1'b0;
				imem_read 	<= 1'b0;
				imem_write 	<= 1'b0;
				pc_increment		<= 1'b0;
				alu_reg_enable		<= 1'b0;
				pc_enable			<= 1'b0;
				alu_src_B			<= 1'b0;//src is 0 to load from Rs2
				alu_out_reg_enable		<= 1'b1;//enable the register to load the alu_out
				dmem_out_reg_enable		<= 1'b0;
				reg_write_enable			<= 1'b0;
				select_reg_write_data	<= 2'b00;
			end
			`AOPIMM: begin
				ir_enable 	<= 1'b0;
				dmem_read 	<= 1'b0;
				dmem_write 	<= 1'b0;
				imem_read 	<= 1'b0;
				imem_write 	<= 1'b0;
				pc_increment		<= 1'b0;
				alu_reg_enable		<= 1'b0;
				pc_enable			<= 1'b0;
				alu_src_B			<= 1'b1;//src is 1 to load from rs2 & immediate concatenation
				alu_out_reg_enable		<= 1'b1;//enable the register to load the alu_out
				dmem_out_reg_enable		<= 1'b0;
				reg_write_enable			<= 1'b0;
				select_reg_write_data	<= 2'b00;
			end
			`LOADW: begin
				ir_enable 	<= 1'b0;
				dmem_read 	<= 1'b0;
				dmem_write 	<= 1'b0;
				imem_read 	<= 1'b0;
				imem_write 	<= 1'b0;
				pc_increment		<= 1'b0;
				alu_reg_enable		<= 1'b0;
				pc_enable			<= 1'b0;
				alu_src_B			<= 1'b0;
				alu_out_reg_enable		<= 1'b0;
				dmem_out_reg_enable		<= 1'b0;
				reg_write_enable			<= 1'b1; //need to write word from memory into register
				select_reg_write_data	<= 2'b00;//this needs to be 0 to select from dmem
			end
			`LOADI: begin
				ir_enable 	<= 1'b0;
				dmem_read 	<= 1'b0;
				dmem_write 	<= 1'b0;
				imem_read 	<= 1'b0;
				imem_write 	<= 1'b0;
				pc_increment		<= 1'b0;
				alu_reg_enable		<= 1'b0;
				pc_enable			<= 1'b0;
				alu_src_B			<= 1'b0;
				alu_out_reg_enable		<= 1'b0;
				dmem_out_reg_enable		<= 1'b0;
				reg_write_enable			<= 1'b1; //need to write word from memory into register
				select_reg_write_data	<= 2'b01;//this needs to be 1 to select from immediate
			end
			`STOREW: begin
				ir_enable 	<= 1'b0;
				dmem_read 	<= 1'b0;
				dmem_write 	<= 1'b1;//have to write to dmem
				imem_read 	<= 1'b0;
				imem_write 	<= 1'b0;
				pc_increment		<= 1'b0;
				alu_reg_enable		<= 1'b0;
				pc_enable			<= 1'b0;
				alu_src_B			<= 1'b0;
				alu_out_reg_enable		<= 1'b0;
				dmem_out_reg_enable		<= 1'b0;
				reg_write_enable			<= 1'b0;
				select_reg_write_data	<= 2'b00;
			end
			`BRANCH: begin
				ir_enable 	<= 1'b0;
				dmem_read 	<= 1'b0;
				dmem_write 	<= 1'b0;
				imem_read 	<= 1'b0;
				imem_write 	<= 1'b0;
				pc_increment		<= 1'b0;
				alu_reg_enable		<= 1'b0;
				if(result_lsb == 1'b1) begin
					pc_enable 		<= 1'b1;//if alu_out == 1, then a branch should be taken. enable pc to input address from Rd & Imm concatenated
				end else begin
					pc_enable 		<= 1'b0;
				end
				alu_src_B			<= 1'b0;
				alu_out_reg_enable		<= 1'b0;
				dmem_out_reg_enable		<= 1'b0;
				reg_write_enable			<= 1'b0;
				select_reg_write_data	<= 2'b00;
			end
			`RTYPE: begin
				ir_enable 	<= 1'b0;
				dmem_read 	<= 1'b0;
				dmem_write 	<= 1'b0;
				imem_read 	<= 1'b0;
				imem_write 	<= 1'b0;
				pc_increment		<= 1'b0;
				alu_reg_enable		<= 1'b0;
				pc_enable			<= 1'b0;
				alu_src_B			<= 1'b0;
				alu_out_reg_enable		<= 1'b0;
				dmem_out_reg_enable		<= 1'b0;
				reg_write_enable			<= 1'b1; //need to write from alu_out to register
				select_reg_write_data	<= 2'b10;//this needs to be 1 to select from alu_out
			end
			endcase
		end
	end
	
endmodule
