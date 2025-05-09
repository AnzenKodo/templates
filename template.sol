// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract BasicContract {
    address public owner;
    uint256 public value;

    constructor() {
        owner = msg.sender;
        value = 0;
    }

    modifier onlyOwner() {
        require(msg.sender == owner, "Only owner can call this function");
        _;
    }

    function setValue(uint256 _newValue) public onlyOwner {
        value = _newValue;
    }

    function getValue() public view returns (uint256) {
        return value;
    }

    function withdraw() public onlyOwner {
        payable(owner).transfer(address(this).balance);
    }

    receive() external payable {}
}
