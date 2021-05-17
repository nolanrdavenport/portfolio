`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    16:10:26 05/08/2021 
// Design Name: 
// Module Name:    multicycle_computer 
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
module multicycle_computer(
   input clk,
	input program_mode,
	input reset,
	input [26:0] external_write_data,
	input [13:0] address,
	output [23:0] alu_out
   );
	
	supply1 VDD;
	
	wire clock_delayed_0;
	assign #5 clock_delayed_0 = clk;
	wire clock_delayed_1;
	assign #10 clock_delayed_1 = clk;
	wire clock_delayed_2;
	assign #15 clock_delayed_2 = clk;
			
	// control
	wire ir_enable;
	wire dmem_read;
	wire dmem_write;
	wire imem_read;
	wire imem_write;
	wire pc_increment;
	wire alu_reg_enable;
	wire pc_enable;
	wire alu_src_B;
	wire alu_out_reg_enable;
	wire dmem_out_reg_enable;
	wire reg_write_enable;
	wire [1:0] select_reg_write_data;

	// program counter
	wire [13:0] pc_address_out;	// TODO: clean this
	
	// memory
	wire [23:0] dmem_read_data;
	wire [23:0] dmem_out_reg_data;
	wire [26:0] imem_read_data;
	
	// instruction register
	wire [3:0] opcode;
	wire [4:0] reg_dest;
	wire [4:0] reg_source_1;
	wire [4:0] reg_source_2;
	wire [7:0] immediate;
	
	// register file
	wire [23:0] data_reg_1;
	wire [23:0] data_reg_2;
	wire [23:0] store_mux_output;
	wire [23:0] reg_write_data_mux_output;
	
	// data registers
	wire [23:0] alu_A_in_data;
	wire [23:0] alu_B_in_data;
	
	// alu_src_mux
	wire [23:0] alu_src_mux_output;
	
	// ALU
	wire [23:0] alu_result; // this is the raw output of the register
	
	control control (
		.program_mode(program_mode), 
		.clk(clk), 
		.reset(reset), 
		.opcode(opcode),
		.result_lsb(alu_out[0]),
		.ir_enable(ir_enable),
		.dmem_read(dmem_read), 
		.dmem_write(dmem_write),
		.imem_read(imem_read), 
		.imem_write(imem_write),
		.pc_increment(pc_increment), 
		.alu_reg_enable(alu_reg_enable),
		.pc_enable(pc_enable),
		.alu_src_B(alu_src_B),
		.alu_out_reg_enable(alu_out_reg_enable),
		.dmem_out_reg_enable(dmem_out_reg_enable),
		.reg_write_enable(reg_write_enable),
		.select_reg_write_data(select_reg_write_data)
	);

	program_counter pc (
		.clk(clock_delayed_0), 
		.enable(pc_enable), 
		.pc_increment(pc_increment), 
		.address_in({ 1'b1, reg_dest, immediate }), 
		.address_out(pc_address_out)
	);
	
	data_memory	dmem (
		.write_enable(dmem_write), 
		.read_enable(dmem_read), 
		.clk(clock_delayed_0), 
		.address({1'b0, reg_source_2, immediate }), 
		.write_data(alu_A_in_data), 
		.read_data(dmem_read_data)
	);
	
	data_register dmem_out_reg(
		.clk(clock_delayed_1),
		.enable(dmem_out_reg_enable),
		.data_in(dmem_read_data),
		.data_out(dmem_out_reg_data)
	);
	
	instruction_memory imem (
		.write_enable(imem_write), 
		.read_enable(imem_read), 
		.clk(clock_delayed_0), 
		.address(pc_address_out), 
		.write_data(external_write_data), 
		.read_data(imem_read_data)
	);
	
	instruction_register ir (
		.clk(clock_delayed_1), 
		.enable(ir_enable), 
		.reset(reset), 
		.instruction_in(imem_read_data),
		.opcode(opcode), 
		.reg_dest(reg_dest), 
		.reg_source_1(reg_source_1), 
		.reg_source_2(reg_source_2), 
		.immediate(immediate)
	);
	
	mux_3_choice reg_write_data_mux (
		.clk(clock_delayed_0),
		.choice_0(dmem_out_reg_data), 
		.choice_1({ 11'b0,reg_source_2, immediate }), 
		.choice_2(alu_out),
		.select(select_reg_write_data), 
		.mux_output(reg_write_data_mux_output)
   );
	
	register_file rf (
		.clk(clock_delayed_1), 
		.write_enable(reg_write_enable),
		.read_enable(alu_reg_enable),
		.read_reg_1(reg_source_1), 
		.read_reg_2(reg_source_2), 
		.write_reg(reg_dest), 
		.write_data(reg_write_data_mux_output), 
		.data_reg_1(data_reg_1), 
		.data_reg_2(data_reg_2)
	);
	
	data_register alu_A_in(
		.clk(clk),
		.enable(alu_reg_enable),
		.data_in(data_reg_1),
		.data_out(alu_A_in_data)
	);
	
	data_register alu_B_in(
		.clk(clk),
		.enable(alu_reg_enable),
		.data_in(data_reg_2),
		.data_out(alu_B_in_data)
	);
	
	mux_2_choice alu_B_mux(
		.clk(clock_delayed_0),
		.choice_0(alu_B_in_data),
		.choice_1({ 11'b0,reg_source_2, immediate }), // TODO: sign extend instead of padding with zeros. 
		.select(alu_src_B),
		.mux_output(alu_src_mux_output)
	);
	
	ALU alu (
		.clk(clock_delayed_1),
		.A_in(alu_A_in_data), 
		.B_in(alu_src_mux_output), 
		.alu_control(opcode), 
		.alu_result(alu_result)
	);
	
	data_register alu_out_reg(
		.clk(clock_delayed_2),
		.enable(alu_out_reg_enable),
		.data_in(alu_result),
		.data_out(alu_out)
	);

endmodule
